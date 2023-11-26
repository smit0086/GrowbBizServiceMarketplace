"use client";

import React from "react";

class Rating extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            stars: [],
            rating: 0,
            hovered: 0,
            selectedIcon: "★",
            deselectedIcon: "☆",
        };

        let outOf = props.outOf ? props.outOf : 5;

        for (var i = 0; i < outOf; i++) {
            this.state.stars.push(i + 1);
        }
        if (props.rating) this.state.rating = props.rating;
    }

    changeRating(newRating) {
        if (this.props.readOnly) return;
        this.setState({
            rating: newRating,
        });

        if (this.props.onChange) this.props.onChange(newRating);
    }

    hoverRating(rating) {
        if (this.props.readOnly) return;
        this.setState({
            hovered: rating,
        });
    }

    render() {
        const { stars, rating, hovered, deselectedIcon, selectedIcon } =
            this.state;

        return (
            <div>
                <div
                    className="rating"
                    style={{ fontSize: "1.8em", color: "#ff851b" }}
                >
                    {stars.map((star) => {
                        return (
                            <span
                                key={star}
                                style={{
                                    cursor: this.props.readOnly
                                        ? "auto"
                                        : "pointer",
                                }}
                                onClick={() => {
                                    this.changeRating(star);
                                }}
                                onMouseEnter={() => {
                                    this.hoverRating(star);
                                }}
                                onMouseLeave={() => {
                                    this.hoverRating(0);
                                }}
                            >
                                {rating < star
                                    ? hovered < star
                                        ? deselectedIcon
                                        : selectedIcon
                                    : selectedIcon}
                            </span>
                        );
                    })}
                </div>
            </div>
        );
    }
}

export default Rating;
