import { React, useEffect, useState } from 'react';
import {useParams} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';
import { YearSelector } from '../components/YearSelector';

import './MatchPage.scss';

export const MatchPage = () => {


    const [matches, setMatches] = useState([]); 
    const {teamName, year} = useParams();

    useEffect(
        () => {
            const fetchMatches = async () => {
                const reponse = await fetch(`http://localhost:8087/team/${teamName}/matches?year=${year}`);
                const data = await reponse.json();
                console.log(data);
                setMatches(data);
            };

            fetchMatches();
        }, [teamName, year]
    );

    return (
        <div className="MatchPage">
            <div className="year-selector">
                <h3>Select Year</h3>
                <YearSelector teamName={teamName}/>
            </div>
            <div>
                <h1>{teamName}'s matches in {year}</h1>
                {matches.map(match => <MatchDetailCard teamName={teamName} match={match} />)}
            </div>
        </div>
    );
}
