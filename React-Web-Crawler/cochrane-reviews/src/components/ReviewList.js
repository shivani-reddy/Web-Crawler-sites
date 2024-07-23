import React, { useState, useEffect, useRef } from "react";
import ReviewItem from "./Reviewitem";
import axios from "axios";
import { FixedSizeList as List } from "react-window";
//import { VariableSizeList as List } from "react-window";
import "../App.css";

function ReviewList({ selectedTopic }) {
  const [data, setData] = useState([]);
  const [topic, setTopic] = useState();
  const [currentIndex, setCurrentIndex] = useState(0);
  const batchSize = 10;
  const loader = useRef(null);

  //Makes the JSON to flat JSON
  useEffect(() => {
    fetch("cochrane_reviews.json")
      .then((response) => response.json())
      .then((data) => {
        //console.log("Data type is ", typeof data, "Data is ", data);
        const flatData = [];
        const topics = [];
        console.log("SelectedTopic ", selectedTopic);
        if (selectedTopic == "" || selectedTopic == undefined) {
          data.forEach((element) => {
            // console.log(element[0].topic);

            element.forEach((singleElement) => {
              flatData.push(singleElement);
            });
          });
        } else {
          data.forEach((element) => {
            if (selectedTopic == element[0].topic) {
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
  }, []);

  //   useEffect(() => {
  //     const options = {
  //       root: null,
  //       rootMargin: "0px",
  //       threshold: 0.1,
  //     };

  //     const observer = new IntersectionObserver(handleObserver, options);

  //     if (loader.current) {
  //       observer.observe(loader.current);
  //     }

  //     return () => {
  //       if (loader.current) {
  //         observer.unobserve(loader.current);
  //       }
  //     };
  //   }, [loader]);

  //   const handleObserver = (entities) => {
  //     const target = entities[0];
  //     if (target.isIntersecting) {
  //       loadMoreData();
  //     }
  //   };

  //   const loadMoreData = () => {
  //     const endIndex = Math.min(currentIndex + batchSize, data.length);
  //     setCurrentIndex(endIndex);
  //   };

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
        <p style={{ color: "#962d91" }}>Date: {item.date}</p>
      </div>
    );
  };

  // const rowHeights = new Array(1000)
  //   .fill(true)
  //   .map(() => 25 + Math.round(Math.random() * 50));

  // const getItemSize = (index) => rowHeights[index];

  return (
    <div className="App">
      {/* {data.slice(0, currentIndex).map((item, index) => (
          //   <div key={index} className="row">
          //     {JSON.stringify(item)}
          //     </div>
          <div className="review-item">
            <a href={item.url} target="_blank" rel="noopener noreferrer">
              {item.title}
            </a>
            <p>Topic:{item.topic}</p>
            <p>Author:{item.author}</p>
            <p>Index:{index}</p>
            <p style={{ color: "#962d91" }}>Date: {item.date}</p>
          </div>
        ))} */}
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

//   return (
//     <div className="review-list">
//       {reviews.map((review, index) => (
//         <ReviewItem key={index} review={review} />
//       ))}
//     </div>
//   );
// };
export default ReviewList;
