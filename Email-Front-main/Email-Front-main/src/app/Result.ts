import {Email} from "./Email/Email";

export class Result {
  public error: boolean;
  public result: number;
  public message: string;
  public emails: Email[];

  constructor(error: boolean, result: number, message: string, emails: Email[]) {
    this.error = error;
    this.result = result;
    this.message = message;
    this.emails = emails;
  }

  geterror(): boolean {
    return this.error;
  }

  getResult(): number {
    return this.result;
  }

  getmessage(): string {
    return this.message;
  }


  seterror(value: boolean) {
    this.error = value;
  }

  setresult(value: number) {
    this.result = value;
  }

  setmessage(value: string) {
    this.message = value;
  }

  setemails(list: Email[]){
    this.emails = list;
  }

  getemails(): Email[] {
    return this.emails;
  }
}

