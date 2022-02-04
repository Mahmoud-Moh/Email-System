import { TestBed } from '@angular/core/testing';
import { mailComponent } from './mail.component';

describe('mail.', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        mailComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(mailComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'untitled'`, () => {
    const fixture = TestBed.createComponent(mailComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('untitled');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(mailComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain('untitled app is running!');
  });
});
