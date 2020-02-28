import React, { Component } from "react";
import styled from "styled-components";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import ListGroup from "react-bootstrap/ListGroup";
import { connect } from "react-redux";
import { setAddMealModalVisibility } from "../../actions/UIActions";
import { recordMealEntry } from "../../api/MealsHistoryAPI";
import { saveMealEntries } from "../../actions/mealsHistoryActions";
import { saveMeals } from "../../actions/mealsActions";
import {
  addUserMeal,
  findMealsByName,
  findMealsByBarcode
} from "../../api/MealsAPI";
import { toast } from "react-toastify";
import MealsListItem from "./MealsListItem";
import DietButton from "../DietButton/DietButton";

const BodyContent = styled.div`
  max-height: 65vh;
  overflow: auto;

  & > p {
    margin-top: 12px;
    margin-left: 2rem;
    font-style: italic;
  }

  ::-webkit-scrollbar {
    width: 16px;
  }

  ::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  ::-webkit-scrollbar-thumb {
    background: #ffb44f;
    border-radius: 8px;
  }
`;

class AddMealModal extends Component {
  state = {
    inputMeal: {
      name: "",
      carbs: 0,
      fat: 0,
      protein: 0,
      kcal: 0
    },
    selectedMeal: {
      name: "",
      weightGram: 0,
      nutrients: {
        carbs: 0,
        fat: 0,
        protein: 0,
        kcal: 0
      }
    },
    selectedFoundMeal: {
      name: "",
      weightGram: 0,
      nutrients: {
        carbs: 0,
        fat: 0,
        protein: 0,
        kcal: 0
      }
    },
    mealsFound: [],
    activeKey: "select",
    searchedName: "",
    searchedBarcode: "",
    searchLoading: false
  };

  hideModal = () => {
    this.props.setMealModalVisibility(false);
  };

  saveSelectedMealToState = (meal, weightGram) => {
    this.setState({
      selectedMeal: { ...meal, weightGram }
    });
  };

  saveSelectedFoundMealToState = (meal, weightGram) => {
    this.setState({
      selectedFoundMeal: { ...meal, weightGram }
    });
  };

  handleAddMealSubmit = e => {
    e.preventDefault();
    this.addUserMeal();
  };

  handleChange = e => {
    this.setState({
      inputMeal: { ...this.state.inputMeal, [e.target.name]: e.target.value }
    });
  };

  handleSearchChange = e => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleRegisterAndAddMeal = (weightGram, name, nutrients) => {
    this.recordMealEntry(weightGram, name, nutrients);
    this.addUserMeal(name, nutrients);
  };

  recordMealEntry = (
    weightGram = this.state.selectedMeal.weightGram,
    name = this.state.selectedMeal.name,
    nutrients = {
      carbs: this.state.selectedMeal.carbs,
      fat: this.state.selectedMeal.fat,
      protein: this.state.selectedMeal.protein,
      kcal: this.state.selectedMeal.kcal
    }
  ) => {
    if (weightGram === 0) {
      toast.error("You forgot to input meal's weight");
      return;
    }
    recordMealEntry({
      name,
      weightGram,
      nutrients
    })
      .then(mealEntries => {
        this.props.saveMealEntries(mealEntries);
        toast.success("Meal successfully registered!");
      })
      .catch(reason => console.error("save entry", reason));
  };

  addUserMeal = (
    name = this.state.inputMeal.name,
    nutrients = {
      carbs: this.state.inputMeal.carbs,
      fat: this.state.inputMeal.fat,
      protein: this.state.inputMeal.protein,
      kcal: this.state.inputMeal.kcal
    }
  ) => {
    addUserMeal({
      name,
      nutrients
    })
      .then(userMeals => {
        this.props.saveUserMeals(userMeals);
        toast.success("Meal successfully added");
      })
      .catch(reason => console.error("save meal", reason));
  };

  handleSearchSubmit = e => {
    e.preventDefault();
    const { searchedName, searchedBarcode } = this.state;
    if (!searchedName && !searchedBarcode) return;
    if (searchedName && searchedName.length < 3) {
      toast.error("Name needs to be at least 2 characters long");
      return;
    } else if (searchedBarcode && searchedBarcode.length < 13) {
      toast.error("Barcode needs to be at least 13 digits long");
      return;
    } else if (searchedName && !searchedBarcode) {
      this.setState({ searchLoading: true });
      findMealsByName(searchedName)
        .then(meals => this.setState({ mealsFound: meals }))
        .catch(error => console.log(error))
        .finally(() => this.setState({ searchLoading: false }));
    } else if (searchedBarcode && !searchedName) {
      this.setState({ searchLoading: true });
      findMealsByBarcode(searchedBarcode)
        .then(meals => this.setState({ mealsFound: meals }))
        .catch(error => console.log(error))
        .finally(() => this.setState({ searchLoading: false }));
    } else {
      this.setState({ searchLoading: true });
      Promise.all([
        findMealsByName(searchedName),
        findMealsByBarcode(searchedBarcode)
      ])
        .then(values => console.log(values))
        .finally(() => this.setState({ searchLoading: false }));
    }
  };

  modalTitles = {
    select: "Record eating a meal",
    manually: "Create a meal",
    find: "Find a meal"
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
            {this.modalTitles[this.state.activeKey]}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Tabs
            activeKey={this.state.activeKey}
            onSelect={key => this.setState({ activeKey: key })}
          >
            <Tab eventKey="select" title="Choose from your meals">
              <BodyContent>
                {this.props.meals.length === 0 ? (
                  <p>
                    No meals here yet. You can create or search for one in other
                    tabs
                  </p>
                ) : (
                  <ListGroup>
                    {this.props.meals
                      .sort((a, b) => b.id - a.id)
                      .map((meal, index) => (
                        <MealsListItem
                          meal={meal}
                          recordMeal={this.recordMealEntry}
                          key={index}
                          saveSelectedMealToState={this.saveSelectedMealToState}
                        />
                      ))}
                  </ListGroup>
                )}
              </BodyContent>
              <Modal.Footer>
                <Button variant="danger" onClick={this.hideModal}>
                  Cancel
                </Button>
              </Modal.Footer>
            </Tab>
            <Tab eventKey="manually" title="Create meal">
              <Form onSubmit={this.handleAddMealSubmit}>
                <BodyContent>
                  <Form.Text>
                    Creating a meal will add it to your list. It will not mark
                    it as if you've eaten it.
                  </Form.Text>
                  <Form.Group>
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                      required
                      type="text"
                      placeholder="Meal's name"
                      value={this.state.inputMeal.name}
                      name="name"
                      onChange={this.handleChange}
                    />
                  </Form.Group>
                  <Form.Text>
                    Following inputs refer to amount in 100 grammes of the meal.
                  </Form.Text>
                  <Form.Group>
                    <Form.Label>Carbohydrates</Form.Label>
                    <InputGroup>
                      <Form.Control
                        required
                        type="number"
                        placeholder="Grammes of carbs in 100g"
                        value={this.state.inputMeal.carbs}
                        name="carbs"
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
                    <Form.Label>Fat</Form.Label>
                    <InputGroup>
                      <Form.Control
                        required
                        type="number"
                        placeholder="Grammes of fat in 100g"
                        value={this.state.inputMeal.fat}
                        name="fat"
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
                    <Form.Label>Protein</Form.Label>
                    <InputGroup>
                      <Form.Control
                        required
                        type="number"
                        placeholder="Grammes of protein in 100g"
                        value={this.state.inputMeal.protein}
                        name="protein"
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
                    <Form.Label>Kcal</Form.Label>
                    <InputGroup>
                      <Form.Control
                        required
                        type="number"
                        placeholder="Kilocalories in 100g"
                        value={this.state.inputMeal.kcal}
                        name="kcal"
                        onChange={this.handleChange}
                        min={0}
                      />
                      <InputGroup.Append>
                        <InputGroup.Text>kcal</InputGroup.Text>
                      </InputGroup.Append>
                    </InputGroup>
                  </Form.Group>
                </BodyContent>
                <Modal.Footer>
                  <DietButton variant="primary" type="submit">
                    Add to your list
                  </DietButton>
                  <Button variant="danger" onClick={this.hideModal}>
                    Cancel
                  </Button>
                </Modal.Footer>
              </Form>
            </Tab>
            <Tab eventKey="find" title="Find in public database">
              <Form
                onSubmit={this.handleSearchSubmit}
                style={{ marginTop: 14 }}
              >
                <InputGroup>
                  <Form.Control
                    value={this.state.searchedName}
                    name="searchedName"
                    minLength={2}
                    onChange={this.handleSearchChange}
                    placeholder="Enter searched meal's name"
                  />
                  <Form.Control
                    value={this.state.searchedBarcode}
                    name="searchedBarcode"
                    minLength={13}
                    onChange={this.handleSearchChange}
                    placeholder="Or its barcode"
                  />
                  <InputGroup.Append>
                    <DietButton
                      style={{ paddingLeft: 20, paddingRight: 20 }}
                      type="submit"
                      disabled={this.state.searchLoading}
                    >
                      {this.state.searchLoading ? "Loading..." : "Search"}
                    </DietButton>
                  </InputGroup.Append>
                </InputGroup>
              </Form>
              <BodyContent>
                {this.state.mealsFound.length === 0 ? (
                  <p>Search results will show up here</p>
                ) : (
                  <ListGroup>
                    {this.state.mealsFound.map((meal, index) => (
                      <MealsListItem
                        meal={meal}
                        key={index}
                        recordMeal={this.handleRegisterAndAddMeal}
                        saveSelectedMealToState={
                          this.saveSelectedFoundMealToState
                        }
                      />
                    ))}
                  </ListGroup>
                )}
              </BodyContent>
              <Modal.Footer>
                <DietButton
                  onClick={() =>
                    this.handleRegisterAndAddMeal(
                      this.state.selectedFoundMeal.weightGram,
                      this.state.selectedFoundMeal.name,
                      this.state.selectedFoundMeal.nutrients
                    )
                  }
                >
                  Save to your list and register as eaten
                </DietButton>
                <Button variant="danger" onClick={this.hideModal}>
                  Cancel
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
