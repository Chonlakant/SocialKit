package co.aquario.socialkit.event;

import co.aquario.socialkit.model.FbProfile;

public class LoadFbProfileEvent {

    public FbProfile profile;

    public LoadFbProfileEvent(FbProfile profile) {
        this.profile = profile;
    }
}
