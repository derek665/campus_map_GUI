/* Hold elements of the CampusPaths React APP */

import React, {Component} from 'react';
import Map from './Map.js';
import {MenuItem, Select} from "@material-ui/core";
import * as fetch from "node-fetch";

class MainContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            start: "",
            end: "",
        };
    };

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

    // reset everything back to start state
    reset = () => {
        this.setState({start:"", end:""})
    };

    render() {
        let menuItems = this.getBuildingList();
        return (
            <div>
                <Map start={this.state.start} end={this.state.end} reset={this.reset}/>
                <div className="center-text">
                    Start: {this.state.start}
                    &nbsp; &nbsp; &nbsp;
                    End: {this.state.end}
                    <div>
                        <Select onChange={this.startChange} value={this.state.start}>{menuItems} </Select>
                        &nbsp; &nbsp; &nbsp;
                        <Select onChange={this.endChange} value={this.state.end}>{menuItems} </Select>
                    </div>
                </div>
            </div>
        )

    }

}

export default MainContainer;