import React, { useState, useEffect, useRef } from "react";
import ReviewList from "./ReviewList";
import SearchBox from "./SearchBox";
import "../App.css";

function MainPage() {
  //Function to handle the search
  
const [topicChanged, setTopicChanged] = useState("");
  useEffect(() => {
    console.log("Topic changes: ",topicChanged)
}, [topicChanged]);
  

   const topicHandler = (topic) => {
     console.log("Setting selected topic:", topic); // Debugging line
     setTopicChanged(topic);
   };

  return (
    <div className="App">
      <div className="Head">
        <h1>Cochrane Reviews </h1>
        <SearchBox searchedTopic={topicHandler} />
      </div>
      <ReviewList selectedTopic={topicChanged} />
    </div>
  );
}

export default MainPage;
