import {ngxLoadingAnimationTypes, NgxLoadingConfig} from 'ngx-loading';

export const TOAST_CONFIG = {
    autoDismiss: true,
    maxOpened: 1,
    positionClass: 'toast-bottom-center',
    newestOnTop: true,
    preventDuplicates: true,
    countDuplicates: true,
    resetTimeoutOnDuplicate: true,
};

export const LOADER_CONFIG: NgxLoadingConfig = {
    animationType: ngxLoadingAnimationTypes.doubleBounce,
    backdropBackgroundColour: 'rgba(255,255,255,0.60)',
    backdropBorderRadius: '0.2rem',
    fullScreenBackdrop: false,
    primaryColour: '#e60000',
    secondaryColour: '#e60000',
    tertiaryColour: '#e60000',
};
