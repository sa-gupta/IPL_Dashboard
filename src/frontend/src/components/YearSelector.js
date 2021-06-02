import { React, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import './YearSelector.scss';

export const YearSelector = ({teamName}) => {
    // let years = [];
    // const startYear = process.env.REACT_APP_DATA_START_YEAR;
    // const endYear = process.env.REACT_APP_DATA_END_YEAR;

    const [years, setYears] = useState([]); 
    // const {teamName} = useParams();

    useEffect(
        () => {
            const fetchYears = async () => {
                const reponse = await fetch(`http://localhost:8087/team/${teamName}/activeYears`);
                const data = await reponse.json();
                console.log(data);
                setYears(data);
            };

            fetchYears();
        }, []
    );

    return (
        <div className="YearSelector">
            {
              years.map(year => (
                <Link to={`/teams/${teamName}/matches/${year}`}><h5>{year}</h5></Link>)
              )
            }
        </div>
    )
}