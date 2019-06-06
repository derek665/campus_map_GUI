/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

/* A simple grid with a variable size */
/* Most of the assignment involves changes to this class */

import React, {Component} from 'react';
import Button from './Button'

class Grid extends Component {
  constructor(props) {
    super(props);
    this.canvasReference = React.createRef();
  }

  componentDidMount() {
    this.redraw();
  }

  componentDidUpdate() {
    this.redraw()
  }

  redraw = () => {
    let ctx = this.canvasReference.current.getContext('2d');
    ctx.clearRect(0, 0, this.props.width, this.props.height);
    var background = new Image();
    background.onload = () => {
      ctx.drawImage(background,3,3);
      let coordinates = this.getCoordinates(this.props.size);
      coordinates.forEach(coordinate => {
        this.drawCircle(ctx, coordinate);
      });
    };
    background.src = "https://courses.cs.washington.edu/courses/cse331/19sp/hws/hw8/image.jpg";
  };

  getCoordinates = (x) => {
    let result = [];
    const gap = 400/(parseInt(x)+ 1);
    let n;
    for (n = gap; n < 400; n+=gap) {
      let j;
      for (j = gap; j < 400; j+=gap) {
        result.push([n, j]);
      }
    }
    return result;
  };

  drawCircle = (ctx, coordinate) => {
    ctx.beginPath();
    ctx.arc(coordinate[0], coordinate[1], 20 / this.props.size, 0, 2 * Math.PI);
    ctx.fill();
  };

  drawHandler = () =>  {
    if (this.props.edges.length > 0) {
      const lines = this.props.edges.split("\n");
      let n;
      const gap = 400 / (parseInt(this.props.size) + 1);

      for (n = 0; n < lines.length; n++) {
        let ctx = this.canvasReference.current.getContext('2d');
        ctx.beginPath();
        let lineIsLength3 = false;
        let pair;

        if (lines[n].includes(" ")) {
          let line = lines[n].split(" ");

          if (line.length === 3) {
            lineIsLength3 = true;
            let pair1 = line[0].split(",");
            let pair2 = line[1].split(",");
            pair = (pair1.length === 2 && pair2.length === 2);

            if (parseInt(pair1[0]) < this.props.size && parseInt(pair1[1]) < this.props.size
                && parseInt(pair2[0]) < this.props.size && parseInt(pair2[1]) < this.props.size) {
              pair1[0] = (parseInt(pair1[0]) + 1) * gap;  // x1
              pair1[1] = (parseInt(pair1[1]) + 1) * gap;  // y1
              pair2[0] = (parseInt(pair2[0]) + 1) * gap;  // x2
              pair2[1] = (parseInt(pair2[1]) + 1) * gap;  // y2
              ctx.moveTo(pair1[0], pair1[1]);
              ctx.lineTo(pair2[0], pair2[1]);
              ctx.strokeStyle = line[2];
              ctx.stroke();
            } else {
              alert("Coordinates out of bound in line " + (parseInt(n) + 1));
            }
          }
        }

        if (!(lineIsLength3 && lines[n].includes(" ") && pair)) {
          alert("Incorrect format for Draw in line " + (parseInt(n) + 1));
        }
      }
    }
  };

  clearHandler = () => {
    this.redraw();
  };

  render() {
    return (
      <div id="canvas-div">
        <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height} />
        <div className="center-text">Current Grid Size: {this.props.size}</div>
        <Button color="primary" onClick={this.drawHandler} value="Draw" />
        <Button color="secondary" onClick={this.clearHandler} value="Clear" />
      </div>
    );
  }
}

export default Grid;
