import React, { useState, useEffect, useRef } from "react";

const ReviewItem = ({ review }) => {
  return (
    <div className="review-item">
      <a href={review.url} target="_blank" rel="noopener noreferrer">
        {review.title}
      </a>
      <p>Topic:{review.topic}</p>
      <p>Author:{review.author}</p>
      <p style={{ color: "#962d91" }}>Date: {review.Date}</p>
    </div>
  );
};

export default ReviewItem;
