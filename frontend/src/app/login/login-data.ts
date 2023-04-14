
export interface LoginData {
  userName: string;
  password: string;
}
export interface RegisterData {

  personalData: PersonalData,
  userCredentials: LoginData

}

export interface PersonalData {
  firstName: string;
  lastName: string;
  email: string;
}

