.PHONY: build-synthetic-producer # Builds Docker image
build-synthetic-producer:
	 ./gradlew :syntheticProducer:jibDockerBuild -Djib.to.image="smuehr/synthetic-producer:latest"