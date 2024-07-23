import React, { useState, useEffect, useRef } from "react";
import ReviewList from "./ReviewList";
import SearchBox from "./SearchBox";
import "../App.css";

function MainPage() {
  //Function to handle the search
  
const [topicChanged, setTopicChanged] = useState("");
  useEffect(() => {
    console.log("VVVVVV ",topicChanged)
}, [topicChanged]);
  

  function topicHandler(e) {
    console.log("this is topic", e);
    console.log("The topic is ", topicChanged);
    setTopicChanged(e);
    console.log("The topic is now ", topicChanged);
  }

  return (
    <div className="App">
      <div className="Head">
        <h1>Cochrane Reviews </h1>
        {/* <SearchBox onSearch={handleSearch} /> */}
        <SearchBox searchedTopic={topicHandler} />
      </div>
      <ReviewList selectedTopic={topicChanged} />
    </div>
  );
}

export default MainPage;
