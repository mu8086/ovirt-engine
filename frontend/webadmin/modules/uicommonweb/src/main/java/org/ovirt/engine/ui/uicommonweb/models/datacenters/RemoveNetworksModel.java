package org.ovirt.engine.ui.uicommonweb.models.datacenters;

import java.util.ArrayList;

import org.ovirt.engine.core.common.action.AddNetworkStoragePoolParameters;
import org.ovirt.engine.core.common.action.VdcActionParametersBase;
import org.ovirt.engine.core.common.action.VdcActionType;
import org.ovirt.engine.core.common.businessentities.Network;
import org.ovirt.engine.core.compat.StringHelper;
import org.ovirt.engine.ui.frontend.Frontend;
import org.ovirt.engine.ui.uicommonweb.Linq;
import org.ovirt.engine.ui.uicommonweb.UICommand;
import org.ovirt.engine.ui.uicommonweb.models.ConfirmationModel;
import org.ovirt.engine.ui.uicommonweb.models.ListModel;
import org.ovirt.engine.ui.uicompat.ConstantsManager;

public class RemoveNetworksModel extends ConfirmationModel{

    private final ListModel sourceListModel;

    public RemoveNetworksModel(ListModel sourceListModel) {
        this.sourceListModel = sourceListModel;

        setTitle(ConstantsManager.getInstance().getConstants().removeLogicalNetworkTitle());
        setHashName("remove_logical_network"); //$NON-NLS-1$
        setMessage(ConstantsManager.getInstance().getConstants().logicalNetworksMsg());

        ArrayList<String> list = new ArrayList<String>();
        for (Network a : Linq.<Network> Cast(sourceListModel.getSelectedItems()))
        {
            list.add(a.getname());
        }
        setItems(list);

        UICommand tempVar = new UICommand("onRemove", this); //$NON-NLS-1$
        tempVar.setTitle(ConstantsManager.getInstance().getConstants().ok());
        tempVar.setIsDefault(true);
        getCommands().add(tempVar);
        UICommand tempVar2 = new UICommand("cancel", this); //$NON-NLS-1$
        tempVar2.setTitle(ConstantsManager.getInstance().getConstants().cancel());
        tempVar2.setIsCancel(true);
        getCommands().add(tempVar2);
    }

    public void onRemove()
    {
        ArrayList<VdcActionParametersBase> pb = new ArrayList<VdcActionParametersBase>();
        for (Network a : Linq.<Network> Cast(sourceListModel.getSelectedItems()))
        {
            pb.add(new AddNetworkStoragePoolParameters(a.getstorage_pool_id().getValue(), a));
        }
        Frontend.RunMultipleAction(VdcActionType.RemoveNetwork, pb);

        sourceListModel.setConfirmWindow(null);
    }

    private void cancel(){
        sourceListModel.setConfirmWindow(null);
    }

 @Override
    public void ExecuteCommand(UICommand command) {
        super.ExecuteCommand(command);
        if (StringHelper.stringsEqual(command.getName(), "onRemove")) //$NON-NLS-1$
        {
            onRemove();
        }
        else if (StringHelper.stringsEqual(command.getName(), "cancel")) //$NON-NLS-1$
        {
            cancel();
        }
    }
}
