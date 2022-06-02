import React, {useState} from 'react'
import Tree from "./components/Tree/Tree";
import { MurmurHash3, Mulberry32 } from "./random";

import "./App.scss";

const App = () => {
  const [seeds, setSeeds] = useState(['ann', 'gary', 'dee']);

  const setSeed = (index, e) => {
    const seedsCopy = seeds.slice();
    seedsCopy[index] = e.target.value;
    setSeeds(seedsCopy);
  }

  const getTree = (seed) => {
    const seeder = MurmurHash3(seed.toString());
    const random = Mulberry32(seeder());
    const tree = Array(5 + Math.round(random() * 7)).fill(null).map((_, i) => (
      {
        angle: (Math.round(random() * 16)/16) * 2*Math.PI,
        length: 5 + Math.round(random() * 20),
      }
    ));
    return tree.sort((a, b) => (b.length-a.length));
  }

  return (
    <div className="App">
      {seeds.map((seed, index) => (
        <div className="vis-container">
          <div className="seed">
            Seed:&nbsp;
            <input type="text" value={seed} onChange={setSeed.bind(this, index)} />
          </div>
          <Tree tree={getTree(seed)} />
        </div>
      ))}
    </div>
  );
}

export default App;
