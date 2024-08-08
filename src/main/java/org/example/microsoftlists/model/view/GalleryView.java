package org.example.microsoftlists.model.view;


import org.example.microsoftlists.model.constants.ViewType;


public class GalleryView implements IView {


    @Override
    public ViewType getViewType() {
        return ViewType.GALLERY;
    }
}
