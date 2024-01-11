export interface UserAccess {
    userId: number;
    ntAccount: string;
    billingAccount: string;
    profileId: number;
    sessionCounter?: number
    rebateLimit?: number,
    debitLimit?: number,
}

