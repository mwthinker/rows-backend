/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-02-12 21:42:27.

export interface Message {
    type: "ERROR" | "S2C_CREATE_GAME" | "S2C_JOIN_GAME" | "S2C_USER" | "S2C_GAME_MOVE" | "S2C_ROOMS" | "C2S_GAME_MOVE" | "C2S_GET_ROOMS" | "C2S_CREATE_GAME" | "C2S_JOIN_GAME" | "C2S_GET_BOARD";
}

export interface Error extends Message {
    type: "ERROR";
    message: string;
}

export interface S2cCreatedGame extends Message {
    type: "S2C_CREATE_GAME";
    gameId: string;
}

export interface S2cJoinedGame extends Message {
    type: "S2C_JOIN_GAME";
    player: Piece;
}

export interface S2cUser extends Message {
    type: "S2C_USER";
    id: string;
}

export interface S2cMoved extends Message {
    type: "S2C_GAME_MOVE";
    gameId: string;
    piece: Piece;
    x: number;
    y: number;
    hash: string;
}

export interface S2cRooms extends Message {
    type: "S2C_ROOMS";
    rooms: Room[];
}

export interface C2sMove extends Message {
    type: "C2S_GAME_MOVE";
    x: number;
    y: number;
}

export interface C2sGetRooms extends Message {
    type: "C2S_GET_ROOMS";
}

export interface C2sCreateGame extends Message {
    type: "C2S_CREATE_GAME";
}

export interface C2sJoinGame extends Message {
    type: "C2S_JOIN_GAME";
    gameId: string;
}

export interface C2sGetBoard extends Message {
    type: "C2S_GET_BOARD";
    gameId: string;
}

export interface Room {
    gameId: string;
    players: Player[];
}

export interface Player {
    piece: Piece;
    id: string;
}

export type Piece = "X" | "O";

export type MessageUnion = Error | S2cCreatedGame | S2cJoinedGame | S2cUser | S2cMoved | S2cRooms | C2sMove | C2sGetRooms | C2sCreateGame | C2sJoinGame | C2sGetBoard;
