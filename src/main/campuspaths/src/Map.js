import React, {Component} from 'react';
import "./Map.css";
import {MenuItem, Select} from "@material-ui/core";
import * as fetch from "node-fetch";
import Button from '@material-ui/core/Button';


class Map extends Component {

  // construct 3 states that represent a start and an end point, and a path between the 2 points
  constructor(props) {
    super(props);
    this.state = {
        start: "",
        end: "",
        path: "",
      }
    this.canvasReference = React.createRef();
    this.backgroundImage = new Image();
    this.backgroundImage.onload = () => {
      this.drawBackgroundImage();
    };
    this.backgroundImage.src = "campus_map.jpg";
  }

  // get the list for the drop down menu for user to select
  getBuildingList = () => {
      let lst = [];
      fetch("http://localhost:4567/buildings").then((res) => {
          if (res.status !== 200) {
              throw Error("Fetch failed");
          }
          return res.json();
      }).then((resText) => {
          let keys = Object.keys(resText);
          keys.forEach((key) => {
              let menuComponent = (<MenuItem value={key}>{resText[key]} </MenuItem>);
              lst.push(menuComponent);
          })
      });
      return lst;
  };

  // modify the start point when user selected one from the list
  startChange = (event) => {
      this.setState({start: event.target.value});
  };

  // modify the end point when user selected one from the list
  endChange = (event) => {
    this.setState({end: event.target.value});
  };

  // draw the map on canvas as the background
  drawBackgroundImage() {
    let canvas = this.canvasReference.current;
    let ctx = canvas.getContext("2d");
    ctx.clearRect(0,0, this.props.width, this.props.height);
    //
    if (this.backgroundImage.complete) { // This means the image has been loaded.
      ctx.drawImage(this.backgroundImage, 0, 0);
    }
  }

  // this function if called when the user clicked on the "Go" button, and draw the path with the endpoints,
  // if the endpoints are the same then it will alert the user and reset the endpoint
  getPath = () => {
      if (this.state.start !== "" && this.state.end !== "" && this.state.start !== this.state.end) {
          fetch("http://localhost:4567/findPath?start=" + this.state.start + "&end=" + this.state.end).then((res) => {
              if (res.status !== 200) {
                  throw Error("Building does not exist");
              }
              return res.json();
          }).then((resText) => {
              this.setState({path: resText.path});
              this.drawPath()
          });
      } else {
          alert("Both end points are the same");
          this.clearHandler();
      }
  };

  // a helper function for getPath that draws the path accordingly
  drawPath = () => {
      let ctx = this.canvasReference.current.getContext('2d');
      ctx.beginPath();
      for (let i = 0; i < this.state.path.length; i++) {
          let seg = this.state.path[i];
          let start = seg.start;
          let end = seg.end;
          ctx.moveTo(start.x, start.y);
          ctx.lineTo(end.x, end.y);
          ctx.strokeStyle = "red";
          ctx.stroke();
      }
  };

  // redraw the background and clear the lines, resetting the endpoints and path to ""
  clearHandler = () => {
      this.drawBackgroundImage();
      this.setState({start: "" , end:"" , path:""});
  }

  render() {
    // that's set up to center the canvas for you. See Map.css for more details.
    // Make sure you set up the React references for the canvas correctly so you
    // can get the canvas object and call methods on it.
      let menuItems = this.getBuildingList();
    return (
      <div className="canvasHolder">
          <canvas ref={this.canvasReference} width={this.backgroundImage.width} height={this.backgroundImage.height} />
          <p>
              Start: {this.state.start}
              &nbsp; &nbsp; &nbsp;
              End: {this.state.end}
          </p>
          <Select onChange={this.startChange} value={this.state.start}>{menuItems} </Select>
          &nbsp; &nbsp; &nbsp;
          <Select onChange={this.endChange} value={this.state.end}>{menuItems} </Select>
          <div className="center-text button">
              <Button variant="contained" color="primary" onClick={this.getPath}>Go </Button>
          </div>
          <div className="center-text button">
              <Button variant="contained" color="secondary" onClick={this.clearHandler}>Clear Path </Button>
          </div>
      </div>
    )
  }
}

export default Map;