export class Account {
  public UserName: string;
  public EmailAddress: string;
  public Pass: string;
  public ID: number;

  constructor(UserName: string, EmailAddress: string, Pass: string, ID: number) {
    this.UserName = UserName;
    this.EmailAddress = EmailAddress;
    this.Pass = Pass;
    this.ID = ID;
  }


  setID(value: number) {
    this.ID = value;
  }


  getID(): number {
    return this.ID;
  }
}
