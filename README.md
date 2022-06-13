# sfg-pet-clinic

SFG Pet Clinic
[![CircleCI](https://circleci.com/gh/Igorgorb/sfg-pet-clinic/tree/main.svg?style=svg&circle-token=b9681c35ab0fc7a3256acde24dac09f4aa253eab)](https://circleci.com/gh/Igorgorb/sfg-pet-clinic/tree/main)

## Compiling the CSS

There is a `petclinic.css` in `src/main/resources/resources/css`. It was generated from the `petclinic.scss` source, combined with the [Bootstrap](https://getbootstrap.com/) library. If you make changes to the `scss`, or upgrade Bootstrap, you will need to re-compile the CSS resources using the Maven profile "css", i.e. `./mvnw package -P css`.

