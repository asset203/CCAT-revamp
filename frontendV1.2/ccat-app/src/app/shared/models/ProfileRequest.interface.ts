import { Feature } from "./feature.interface";
import { Menu } from "./menu.interface";
export interface MonetaryLimits {
    limitId: number;
    limitName: string;
    value: number;
    defaultValue: number;
}
export interface ServiceClasses {
    code: number;
    id: number;
    name: string;
}
export interface ProfileRequest {
    profileName?: string;
    sessionLimit?: number;
    sysFeatures?: Feature[];
    ccFeatures?: Feature[];
    monetaryLimits?: MonetaryLimits[];
    serviceClasses?: ServiceClasses[];
    dssReportsFeatures?:Feature[]
    menus?:Menu[];
    profileId?:number;
}
