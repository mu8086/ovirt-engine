#
# ovirt-engine-setup -- ovirt engine setup
# Copyright (C) 2013-2015 Red Hat, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


"""post install config file plugin."""


import gettext

from otopi import common
from otopi import constants as otopicons
from otopi import filetransaction
from otopi import plugin
from otopi import util

from ovirt_engine_setup import constants as osetupcons


def _(m):
    return gettext.dgettext(message=m, domain='ovirt-engine-setup')


@util.export
class Plugin(plugin.PluginBase):
    """post install config file plugin."""

    def __init__(self, context):
        super(Plugin, self).__init__(context=context)

    @plugin.event(
        stage=plugin.Stages.STAGE_INIT,
    )
    def _init(self):
        self.environment[osetupcons.CoreEnv.GENERATE_POSTINSTALL] = True

    @plugin.event(
        stage=plugin.Stages.STAGE_MISC,
        priority=plugin.Stages.PRIORITY_LAST,
        condition=lambda self: self.environment[
            osetupcons.CoreEnv.GENERATE_POSTINSTALL
        ],
    )
    def _misc(self):
        self.logger.info(
            _("Generating post install configuration file '{name}'").format(
                name=osetupcons.FileLocations.OVIRT_SETUP_POST_INSTALL_CONFIG,
            )
        )
        content = ['[environment:default]\n']
        consts = []
        for constobj in self.environment[
            osetupcons.CoreEnv.SETUP_ATTRS_MODULES
        ]:
            consts.extend(constobj.__dict__['__osetup_attrs__'])
        for c in consts:
            for key in c.__dict__.values():
                if hasattr(key, '__osetup_attrs__'):
                    if key.__osetup_attrs__['postinstallfile']:
                        key = key.fget(None)
                        if key in self.environment:
                            value = self.environment[key]
                            content.append(
                                '{key}={type}:{value}\n'.format(
                                    key=key,
                                    type=common.typeName(value),
                                    value=(
                                        '\n '.join(value)
                                        # We want the next lines to be
                                        # indented, so that
                                        # configparser will treat them
                                        # as a single multi-line value.
                                        # So we join with '\n '.
                                        if isinstance(value, list)
                                        else value
                                    ),
                                )
                            )
        self.environment[otopicons.CoreEnv.MAIN_TRANSACTION].append(
            filetransaction.FileTransaction(
                name=osetupcons.FileLocations.OVIRT_SETUP_POST_INSTALL_CONFIG,
                content=''.join(content),
                binary=True,
                modifiedList=self.environment[
                    otopicons.CoreEnv.MODIFIED_FILES
                ],
            )
        )


# vim: expandtab tabstop=4 shiftwidth=4
