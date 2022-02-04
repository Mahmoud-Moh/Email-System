export class InteractiveChatbox {
  chatbox: any;

  constructor(b: any) {
    this.chatbox = b;
  }

  display() {
    //const {button, chatbox} = this.args;
    this.total_func(this.chatbox);
  }

  /*toggleState(chatbox:any) {
    this.state = !this.state;
    this.showOrHideChatBox(chatbox, this.args.button);
  }

  showOrHideChatBox(chatbox:any, button:any) {
    if(this.state) {
      chatbox.classList.add('chatbox--active')
      this.toggleIcon(true, button);
    } else if (!this.state) {
      chatbox.classList.remove('chatbox--active')
      this.toggleIcon(false, button);
    }
  }

  toggleIcon(state:any, button:any) {
    const { isClicked, isNotClicked } = this.icons;
    let b = button.children[0].innerHTML;

    if(state) {
      button.children[0].innerHTML = isClicked;
    } else if(!state) {
      button.children[0].innerHTML = isNotClicked;
    }
  }*/

  total_func(chatbox: any) {
    console.log("mr stark");
    chatbox.classList.add('chatbox--active');
  }
}
