import React, { Component } from "react";
import styled from "styled-components";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Image from "react-bootstrap/Image";
import ListGroup from "react-bootstrap/ListGroup";
import { connect } from "react-redux";
import { setAddMealModalVisibility } from "../../actions/UIActions";
import { recordMealEntry } from "../../api/MealsHistoryAPI";
import { saveMealEntries } from "../../actions/mealsHistoryActions";
import { saveMeals } from "../../actions/mealsActions";
import { getUserMeals, addUserMeal } from "../../api/MealsAPI";
import { toast } from "react-toastify";

const StyledListGroupItem = styled(ListGroup.Item)`
  display: grid;
  grid-template:
    "image name"
    "image description";
  &&:focus {
    background-color: blue;
  }
`;

const StyledImage = styled(Image)`
  grid-area: image;
  width: 50%;
  &&:after {
    content: "";
    display: block;
    padding-bottom: 100%;
  }
`;

const ListElementContainer = styled.div`
  display: flex;
  flex-direction: row;
`;

const MealsListElement = props => {
  //TODO: dodać wybór wagi posiłku
  return (
    <Form onSubmit={e => e.preventDefault()}>
      <ListElementContainer>
        <StyledListGroupItem
          action
          onFocus={() => {
            props.saveSelectedMealToState(props.meal);
          }}
        >
          <StyledImage src={props.meal.imageLink} />
          <p style={{ gridTemplateAreas: "name" }}>{props.meal.name}</p>
          <p style={{ gridTemplateAreas: "description" }}>
            {props.meal.nutrients.toString()}
          </p>
        </StyledListGroupItem>
        <Button variant="danger">Usuń</Button>
      </ListElementContainer>
    </Form>
  );
};

class AddMealModal extends Component {
  state = {
    name: "",
    imageLink: "",
    weightGram: 0,
    waterMilli: 0,
    carbsGram: 0,
    fatGram: 0,
    proteinGram: 0,
    kcalPer100Gram: 0,
    mealsFound: []
  };

  componentDidMount() {
    if (!this.props.isUserLoggedIn) return;
    getUserMeals()
      .then(data => this.props.saveUserMeals(data))
      .catch(reason => console.error("modal", reason));
  }

  hideModal = () => {
    this.props.setMealModalVisibility(false);
  };

  saveSelectedMealToState = meal => {
    this.setState({
      name: meal.name,
      imageLink: meal.imageLink,
      weightGram: 100,
      waterMilli: meal.nutrients.waterMilli,
      carbsGram: meal.nutrients.carbsGram,
      fatGram: meal.nutrients.fatGram,
      proteinGram: meal.nutrients.proteinGram,
      kcalPer100Gram: meal.nutrients.kcalPer100Gram
    });
  };

  handleSubmit = e => {
    e.preventDefault();
    this.recordMealEntry();
    this.hideModal();
  };

  handleChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };

  recordMealEntry = () => {
    recordMealEntry({
      name: this.state.name,
      imageLink: this.state.imageLink,
      weightGram: this.state.weightGram,
      nutrients: {
        waterMilli: this.state.waterMilli,
        carbsGram: this.state.carbsGram,
        proteinGram: this.state.proteinGram,
        kcalPer100Gram: this.state.kcalPer100Gram
      }
    })
      .then(mealEntries => {
        this.props.saveMealEntries(mealEntries);
        toast.success("Meal successfully registered!");
      })
      .catch(reason => console.error("save entry", reason));
  };

  addUserMeal = () => {
    addUserMeal({
      name: this.state.name,
      imageLink: this.state.imageLink,
      weightGram: this.state.weightGram,
      nutrients: {
        waterMilli: this.state.waterMilli,
        carbsGram: this.state.carbsGram,
        proteinGram: this.state.proteinGram,
        kcalPer100Gram: this.state.kcalPer100Gram
      }
    })
      .then(userMeals => {
        this.props.saveUserMeals(userMeals);
        toast.success("Meal successfully added");
      })
      .catch(reason => console.error("save meal", reason));
  };

  render() {
    return (
      <Modal
        show={this.props.visibility}
        onHide={this.hideModal}
        aria-labelledby="contained-modal-title-vcenter"
        centered
        size="lg"
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            Register a meal
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Tabs defaultActiveKey="manually">
            <Tab eventKey="manually" title="Manualnie">
              <Form onSubmit={this.handleSubmit}>
                <Form.Text>
                  Zatwierdzenie klawiszem Enter spowoduje zapisanie spożycia
                  posiłku
                </Form.Text>
                <Form.Group>
                  <Form.Label>Nazwa</Form.Label>
                  <Form.Control
                    required
                    type="text"
                    placeholder="Meal's name"
                    value={this.state.name}
                    name="name"
                    onChange={this.handleChange}
                  />
                </Form.Group>
                <Form.Group>
                  <Form.Label>Avatar link (optional)</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Paste your avatar url"
                    value={this.state.imageLink}
                    name="imageLink"
                    onChange={this.handleChange}
                  />
                </Form.Group>
                <Form.Group>
                  <Form.Label>Weight</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="number"
                      placeholder="Weight in grammes"
                      value={this.state.weightGram}
                      name="weightGram"
                      onChange={this.handleChange}
                      min={0}
                    />
                    <InputGroup.Append>
                      <InputGroup.Text>g</InputGroup.Text>
                    </InputGroup.Append>
                  </InputGroup>
                </Form.Group>
                <Form.Text>
                  Wszystkie poniższe pola dotyczą zawartości w 100g posiłku
                </Form.Text>
                <Form.Group>
                  <Form.Label>Zawartość wody</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="number"
                      placeholder="Podaj zawartość wody w mililitrach"
                      value={this.state.waterMilli}
                      name="waterMilli"
                      onChange={this.handleChange}
                      min={0}
                      max={100}
                    />
                    <InputGroup.Append>
                      <InputGroup.Text>ml</InputGroup.Text>
                    </InputGroup.Append>
                  </InputGroup>
                </Form.Group>
                <Form.Group>
                  <Form.Label>Zawartość tłuszczu</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="number"
                      placeholder="Podaj zawartość tłuszczu w gramach"
                      value={this.state.fatGram}
                      name="fatGram"
                      onChange={this.handleChange}
                      min={0}
                      max={100}
                    />
                    <InputGroup.Append>
                      <InputGroup.Text>g</InputGroup.Text>
                    </InputGroup.Append>
                  </InputGroup>
                </Form.Group>
                <Form.Group>
                  <Form.Label>Zawartość węglowodanów</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="number"
                      placeholder="Podaj zawartość węglowodanów w gramach"
                      value={this.state.carbsGram}
                      name="carbsGram"
                      onChange={this.handleChange}
                      min={0}
                      max={100}
                    />
                    <InputGroup.Append>
                      <InputGroup.Text>g</InputGroup.Text>
                    </InputGroup.Append>
                  </InputGroup>
                </Form.Group>
                <Form.Group>
                  <Form.Label>Zawartość protein</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      type="number"
                      placeholder="Podaj zawartość protein w gramach"
                      value={this.state.proteinGram}
                      name="proteinGram"
                      onChange={this.handleChange}
                      min={0}
                      max={100}
                    />
                    <InputGroup.Append>
                      <InputGroup.Text>g</InputGroup.Text>
                    </InputGroup.Append>
                  </InputGroup>
                  <Form.Group>
                    <Form.Label>Kcal</Form.Label>
                    <InputGroup>
                      <Form.Control
                        required
                        type="number"
                        placeholder="Podaj ilość kilokalorii"
                        value={this.state.kcalPer100Gram}
                        name="kcalPer100Gram"
                        onChange={this.handleChange}
                        min={0}
                      />
                      <InputGroup.Append>
                        <InputGroup.Text>kcal</InputGroup.Text>
                      </InputGroup.Append>
                    </InputGroup>
                  </Form.Group>
                </Form.Group>

                <Modal.Footer>
                  <Button
                    type="primary"
                    onClick={() => {
                      this.addUserMeal();
                      this.hideModal();
                    }}
                  >
                    Dodaj do swoich posiłków
                  </Button>
                  <Button
                    onClick={() => {
                      this.addUserMeal();
                      this.recordMealEntry();
                      this.hideModal();
                    }}
                  >
                    Zapisz i dodaj do swoich posiłków
                  </Button>
                  <Button variant="success" type="submit">
                    Zapisz
                  </Button>
                  <Button variant="danger" onClick={this.hideModal}>
                    Anuluj
                  </Button>
                </Modal.Footer>
              </Form>
            </Tab>
            <Tab eventKey="select" title="Wybierz ze swoich posiłków">
              {this.props.meals.length === 0 ? (
                <p>Nie ma tu jeszcze żadnych posiłków</p>
              ) : (
                <ListGroup>
                  {this.props.meals.map((meal, index) => (
                    <MealsListElement
                      meal={meal}
                      key={index}
                      saveSelectedMealToState={this.saveSelectedMealToState}
                    />
                  ))}
                </ListGroup>
              )}
              <Modal.Footer>
                <Button
                  variant="success"
                  onClick={() => {
                    this.recordMealEntry();
                    this.hideModal();
                  }}
                >
                  Wybierz
                </Button>
                <Button variant="danger" onClick={this.hideModal}>
                  Anuluj
                </Button>
              </Modal.Footer>
            </Tab>
            <Tab eventKey="find" title="Wyszukaj w bazie posiłków">
              <InputGroup>
                <Form.Control placeholder="Wpisz nazwę szukanego posiłku" />
                <Form.Control placeholder="lub jego kod kreskowy" />
                <InputGroup.Append>
                  <Button>Wyszukaj</Button>
                </InputGroup.Append>
              </InputGroup>
              {this.state.mealsFound.length === 0 ? (
                <p>Tu pojawią się wyniki wyszukiwania</p>
              ) : (
                <ListGroup>
                  {this.state.mealsFound.map((meal, index) => (
                    <MealsListElement
                      meal={meal}
                      key={index}
                      saveSelectedMealToState={this.saveSelectedMealToState}
                    />
                  ))}
                </ListGroup>
              )}
              <Modal.Footer>
                <Button>Wybierz i dodaj do swoich posiłków</Button>
                <Button variant="success">Wybierz</Button>
                <Button variant="danger" onClick={this.hideModal}>
                  Anuluj
                </Button>
              </Modal.Footer>
            </Tab>
          </Tabs>
        </Modal.Body>
      </Modal>
    );
  }
}

const mapStateToProps = state => {
  return {
    visibility: state.UI.addMealModalVisibility,
    meals: state.meals.meals,
    isUserLoggedIn: state.account.isUserLoggedIn
  };
};

const mapDispatchToProps = dispatch => {
  return {
    setMealModalVisibility: isModalVisible =>
      dispatch(setAddMealModalVisibility(isModalVisible)),
    saveMealEntries: mealEntries => dispatch(saveMealEntries(mealEntries)),
    saveUserMeals: userMeals => dispatch(saveMeals(userMeals))
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(AddMealModal);
