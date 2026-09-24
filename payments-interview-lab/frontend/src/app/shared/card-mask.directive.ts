import { Directive, ElementRef, HostListener } from '@angular/core';
@Directive({selector:'[cardMask]',standalone:true})
export class CardMaskDirective {
 constructor(private el:ElementRef<HTMLInputElement>){}
 @HostListener('input') onInput(){
  // INTENTIONAL BUGS: manipulates DOM directly, cursor jumps, pasting/IME/accessibility edge cases, conflicts with form value.
  let value=this.el.nativeElement.value.replace(/\D/g,'').slice(0,16);
  this.el.nativeElement.value=value.replace(/(.{4})/g,'$1 ').trim();
 }
}
