
export interface LoginData {
  userName: string;
  password: string;
}
export interface RegisterData {

  personalData: {
    firstName: string;
    lastName: string;
    email: string;
  },
  userCredentials: {
    userName: string;
    password: string;
  }

}

