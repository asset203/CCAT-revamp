export interface AccountGroupCC {
    id: number;
    name: string;
    bits: Bit[];
}
export interface Bit {
    bitName: string;
    bitPosition: number;
    enabled: boolean;
}
