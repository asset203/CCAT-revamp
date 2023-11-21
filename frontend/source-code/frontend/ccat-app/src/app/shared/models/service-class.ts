export interface ServiceClass {
    code: number,
    id: number,
    name: string
}
export interface ServiceClassPlanDescription{
    planId :number;
    planDescription : string;
    name?: string;
}