/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2025-02-15 18:26:14.

export interface Message {
    type: "ERROR" | "S2C_CREATE_GAME" | "S2C_JOIN_GAME" | "S2C_USER" | "S2C_GAME_MOVE" | "S2C_GAMES" | "S2C_GAME" | "C2S_GAME_MOVE" | "C2S_GET_GAMES" | "C2S_CREATE_GAME" | "C2S_JOIN_GAME" | "C2S_GET_GAME";
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

export interface S2cGames extends Message {
    type: "S2C_GAMES";
    games: Game[];
}

export interface S2cGame extends Message {
    type: "S2C_GAME";
    gameId: string;
    board: Board;
    gameHash: string;
}

export interface C2sMove extends Message {
    type: "C2S_GAME_MOVE";
    x: number;
    y: number;
}

export interface C2sGetGames extends Message {
    type: "C2S_GET_GAMES";
}

export interface C2sCreateGame extends Message {
    type: "C2S_CREATE_GAME";
}

export interface C2sJoinGame extends Message {
    type: "C2S_JOIN_GAME";
    gameId: string;
}

export interface C2sGetGame extends Message {
    type: "C2S_GET_GAME";
    gameId: string;
}

export interface Game {
    gameId: string;
    players: Player[];
}

export interface Board {
    bestOf: number;
    player: Piece;
    cells: Cell[];
}

export interface Player {
    piece: Piece;
    id: string;
}

export interface Cell {
    x: number;
    y: number;
    piece: Piece;
}

export type Piece = "X" | "O";

export type MessageUnion = Error | S2cCreatedGame | S2cJoinedGame | S2cUser | S2cMoved | S2cGames | S2cGame | C2sMove | C2sGetGames | C2sCreateGame | C2sJoinGame | C2sGetGame;
