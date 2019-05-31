import React, {Component} from 'react';
import "./Map.css";

class Map extends Component {

  // NOTE:
  // This component is a suggestion for you to use, if you would like to.
  // It has some skeleton code that helps set up some of the more difficult parts
  // of getting <canvas> elements to display nicely.
  //
  // If you don't want to use this component, you're free to delete it.

  constructor(props) {
    super(props);
    this.backgroundImage = new Image();
    this.backgroundImage.onload = () => {
      // TODO: Do something when the image is ready?
    };
    this.backgroundImage.src = ""; // TODO: Fill this in.
  }

  drawBackgroundImage() {
    let canvas = null; // TODO Fill this in with the canvas, not the context.
    let ctx = canvas.getContext("2d");
    //
    if (this.backgroundImage.complete) { // This means the image has been loaded.
      canvas.width = this.backgroundImage.width;
      canvas.height = this.backgroundImage.height;
      ctx.drawImage(this.backgroundImage, 0, 0);
    }
  }

  render() {
    // TODO: You should put a <canvas> inside the <div>. It has a className
    // that's set up to center the canvas for you. See Map.css for more details.
    // Make sure you set up the React references for the canvas correctly so you
    // can get the canvas object and call methods on it.
    return (
      <div className="canvasHolder">

      </div>
    )
  }
}

export default Map;