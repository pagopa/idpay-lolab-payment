# Local deployment

You can deploy it locally by using minikube.

First of all install minikube and run it
minikube start

Then enable and install the ingress
minikube addons enable ingress

Now build a docker image and make it available to minikube registry.
Allow docker to build to minikube registry.
eval $(minikube docker-env)

Then build image
docker build -t idpay-lolab-payment:latest -f ./Dockerfile .

Now deploy the helm chart called "local"

