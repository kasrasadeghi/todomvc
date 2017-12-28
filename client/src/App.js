import React, {Component} from 'react';


class App extends Component {
  constructor() {
    super();
    this.state = {
      data: [],
      value: ''
    };

    fetch('http://localhost:5000/todos', {
      method: 'GET',
      dataType: 'application/json'
    }).then(r => r.json())
      .then(j => {
        console.log('api response received');
        this.setState({data: j})
      });
  }

  submit(e) {
    console.log(this.state.value);
    fetch('http://localhost:5000/todo', {
      method: 'POST',
      // dataType: 'application/json',
      body: this.state.value
    }).then(r => r.json())
      .then(j => {
        console.log('api response received');
        this.setState({data: j, value: ''})
      });
    e.preventDefault();
  }

  textChange(e) {
    this.setState({value: e.target.value});
    e.preventDefault();
  }

  remove(e, name) {
    e.preventDefault();
    fetch('http://localhost:5000/remove', {
      method: 'POST',
      // dataType: 'application/json',
      body: name
    }).then(r => r.json())
      .then(j => {
        console.log('api response received');
        this.setState({data: j, value: ''})
      });
  }


  render() {
    return (
      <div className="App">
        <a>begin of to-do</a>
        <ul>
          {this.state.data.map((el, i) => (
            <li
              key={i}
              style={{"text-decoration": el.status === "Done" ?
                  "line-through" : "none"}}
              onClick={(e) => this.remove(e,el.name)}>
              {el.name}
            </li>
          ))}
        </ul>
        <form onSubmit={(e) => this.submit(e)}>
          <input type="text"
                 value={this.state.value}
                 onChange={(e) => this.textChange(e)}
          />
          <input type="submit" value="Submit"/>
        </form>
      </div>
    );
  }
}

export default App;
