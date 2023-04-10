
export interface LoginData {
  username: string;
  password: string;
}
export interface RegisterData {
  username: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
}

export var LOGIN_DATA_MOCK: LoginData[] = [
  {
    username: "testUser",
    password: "!@#$"
  },
  {
    username: "mateusz",
    password: "!@#$"
  },{
    username: "anżej",
    password: "!@#$"
  },
]
