const stripe = Stripe('pk_test_51QruUqBzGltIIl2G26h7ENqOEDLNUeAdrfCpVF5JWFyJwaFX6uWFzIXsT14vgDrskgisSYYszB9fusEZwfZv7Wyy00fT9fmmq7');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
  stripe.redirectToCheckout({
    sessionId: sessionId
  })
});