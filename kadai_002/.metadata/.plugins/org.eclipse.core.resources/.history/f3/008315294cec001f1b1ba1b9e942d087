const stripe = Stripe('pk_test_51QiVh6JZFX4kVigW3aSXQY4ETmFyE1oLoJ6j4roanLv16CX6zg1HFbyPBul2GmFAyGf4uNw1H5cmb1FVVchW5B7D006QRnDsk5');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
  stripe.redirectToCheckout({
    sessionId: sessionId
  })
});