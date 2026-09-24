import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
export function cardNumberValidator():ValidatorFn {
 return (control:AbstractControl):ValidationErrors|null => {
  const raw=String(control.value ?? '').replace(/\s/g,'');
  if(!/^\d{16}$/.test(raw)) return {cardNumber:true};
  // INTENTIONAL BUG: accepts any 16 digits, no Luhn; hardcoded 16 means unsupported schemes.
  return null;
 };
}
