## Examples

This is an example on how to use the AgileKip Platform to generate Process Aware Information Systems based on the BuyBook model.

## Running the Platform

| Description                  | Command                                                                                                                    |
| ---------------------------- | -------------------------------------------------------------------------------------------------------------------------- |
| **Start the Container**      | docker container run --name agilekip.v0.0.12 -v $PWD:/home/jhipster/app -d -t agilekip/generator-jhipster-agilekip:v0.0.12 |
| **Enter the Container**      | docker container exec -it agilekip.v0.0.12 bash                                                                            |
| **Generate the App**         | jhipster --blueprints agilekip --skip-jhipster-dependencies                                                                |
| **Model the BPMN**           | -----------                                                                                                                |
| **Model/Configure Entities** | -----------                                                                                                                |
| **Generate all Entities**    | jhipster entity BuyBook --regenerate                                                                                       |
| **Build the System**         | ./mvnw                                                                                                                     |
