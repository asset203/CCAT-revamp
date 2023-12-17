import { Feature } from "./feature.interface";
import { Menu } from "./menu.interface";
import { MonetaryLimits } from "./ProfileRequest.interface";

export interface Profile {
    profileId: number,
    profileName: string,
    features: Feature[],
    authorizedUrls: any,
    serviceClassesIDs: any,
    monetaryLimitsArray: MonetaryLimits[],
    sessionLimit: number,
    isAdjustmentsLimited: boolean,
    menus: Menu[]

}

