import React, {Component} from 'react';
import "./Map.css";
import * as fetch from "node-fetch";
import Button from './Button.js';


class Map extends Component {

  // construct 3 states that represent a start and an end point, and a path between the 2 points
  constructor(props) {
    super(props);
    this.state = {
        path:""
    };
    this.canvasReference = React.createRef();
    this.backgroundImage = new Image();
    this.backgroundImage.onload = () => {
      this.drawBackgroundImage();
    };
    this.backgroundImage.src = "campus_map.jpg";
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
  };

  // this function if called when the user clicked on the "Go" button, and draw the path with the endpoints,
  // if the endpoints are the same then it will alert the user and reset the endpoint
  getPath = () => {
      if (this.props.start !== "" && this.props.end !== "" && this.props.start !== this.props.end) {
          fetch("http://localhost:4567/findPath?start=" + this.props.start + "&end=" + this.props.end).then((res) => {
              if (res.status !== 200) {
                  throw Error("Fetch failed");
              }
              return res.json();
          }).then((resText) => {
              this.setState({path: resText.path});
              this.drawPath()
          });
      } else if (this.props.start !== "" && this.props.end !== "") {
          alert("Both end points are the same");
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
      this.props.reset();
  };

  render() {
    // that's set up to center the canvas for you. See Map.css for more details.
    // Make sure you set up the React references for the canvas correctly so you
    // can get the canvas object and call methods on it.
    return (
      <div className="canvasHolder">
          <canvas ref={this.canvasReference} width={this.backgroundImage.width} height={this.backgroundImage.height} />
          <Button color="primary" onClick={this.getPath} value="Find Path" />
          <Button color="secondary" onClick={this.clearHandler} value="Clear Path" />
      </div>
    )
  }
}

export default Map;