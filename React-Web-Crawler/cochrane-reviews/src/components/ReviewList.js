import React, { useState, useEffect, useRef } from "react";
import { FixedSizeList as List } from "react-window";
import "../App.css";

function ReviewList({ selectedTopic }) {
  const [data, setData] = useState([]);
  const [topic, setTopic] = useState();
  const [currentIndex, setCurrentIndex] = useState(0);
  const batchSize = 10;
  const loader = useRef(null);

  //Makes the JSON to flat JSON
    useEffect(() => {
      console.log("Fetching data in ReviewList:", selectedTopic);//selected topic in reviewlist
    fetch("cochrane_reviews.json")
      .then((response) => response.json())
      .then((data) => {
        //console.log("Data type is ", typeof data, "Data is ", data);
        const flatData = [];
        const topics = [];
        console.log("SelectedTopic ", selectedTopic);
        if ((selectedTopic == "" || selectedTopic == undefined)) {
          data.forEach((element) => {
            //console.log(element[0].topic);

            element.forEach((singleElement) => {
              flatData.push(singleElement);
            });
          });
        } else {
          data.forEach((element) => {
            if (selectedTopic.toLowerCase() === element[0].topic.toLowerCase()) {
              element.forEach((singleElement) => {
                flatData.push(singleElement);
              });
            }
          });
        }

        //console.log("Flat Array ", flatData);
        setData(flatData);
        setTopic(topics);
        setCurrentIndex(batchSize);
      })
      .catch((error) => console.error("Error fetching data:", error));
  }, [selectedTopic]);

  

  const loadMoreData = () => {
    if (currentIndex < data.length) {
      setCurrentIndex((prevIndex) =>
        Math.min(prevIndex + batchSize, data.length)
      );
    }
  };

  const Row = ({ index, style }) => {
    const item = data[index];
    return (
      <div style={style} className="row">
        <a href={item.url} target="_blank" rel="noopener noreferrer">
          {item.title}
        </a>
        <p>Topic:{item.topic}</p>
        <p>Author:{item.author}</p>
        <p className="date">Date: {item.date}</p>
      </div>
    );
  };

  
  return (
    <div className="App">
      {}
      <List
        className="List"
        height={800}
        itemCount={currentIndex}
        itemSize={180}
        // itemSize={getItemSize}
        width={"100%"}
        onItemsRendered={({ visibleStopIndex }) => {
          if (visibleStopIndex >= currentIndex - 1) {
            loadMoreData();
          }
        }}
      >
        {Row}
      </List>
      {currentIndex < data.length && (
        <div ref={loader} className="loading">
          Loading...
        </div>
      )}
    </div>
  );
}


export default ReviewList;
