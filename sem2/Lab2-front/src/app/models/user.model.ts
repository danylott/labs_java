export interface User {
    id: number;
    email: string;
    name: string;
    surname: string;
}

export function getEmptyUser(): User {
    return {
        id: null,
        email: null,
        name: null,
        surname: null
    };
}

export function getUser(idI: number, emailI: string, nameI: string, surnameI: string) {
    const user: User = {
        id: idI,
        email: emailI,
        name: nameI,
        surname: surnameI,
    };

    return user;
}
