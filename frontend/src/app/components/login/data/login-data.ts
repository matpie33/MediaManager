
export interface LoginData {
  userName: string;
  password: string;
}
export interface RegisterData {

  roles: Array<String>,
  personalData: PersonalData,
  userCredentials: LoginData
}

export interface PersonalData {
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
}


export interface LoginResponse {
  permissions: Array<string>;
  id: number;
}
