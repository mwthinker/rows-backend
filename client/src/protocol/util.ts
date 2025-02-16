

export type TypeName<T> = T extends { type: infer U } ? U : never;