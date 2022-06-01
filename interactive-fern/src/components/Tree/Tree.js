import React from 'react'
import {interpolateRgb} from "d3-interpolate";

import "./Tree.scss";

const Tree = ({tree}) => {

    const colorsRange = interpolateRgb("#2f4c82", "#3bc8b7");

    return (
      <div className="ground">
        <div className="tree">
          {tree.map((fern, fi) => (
            <div
              key={fi}
              className="fern"
              style={{
                transform: `rotate(${fern.angle}rad) scale(${Math.sqrt(fern.length)})`,
              }}
            >
              {Array(fern.length).fill(null).map((_, li) => (
                <div
                  key={li}
                  className="leaf"
                  style={{
                    left: `${2 + 3*li}px`,
                    transform: `rotate(${li%2==0 ? '' : '-'}60deg) scale(${1.2-li/fern.length})`,
                  }}
                >
                  <div
                    className="leaf-img"
                    style={{
                      animationDelay: `${100*fi+100*li}ms`,
                      background: colorsRange(fi/tree.length)
                    }}
                  />
                </div>
              ))}
            </div>
          ))}
        </div>
      </div>
    );
}

export default Tree;
