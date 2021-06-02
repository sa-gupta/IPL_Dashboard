import { React, useEffect, useState } from 'react';
import {useParams, Link} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';
import {PieChart} from 'react-minimal-pie-chart';

import './HomePage.scss';
import { TeamTile } from '../components/TeamTile';

export const HomePage = () => {

    const [teams, setTeams] = useState([]);


    useEffect(
        () => {
            const fetchAllTeams = async () => {
                const reponse = await fetch(`http://localhost:8087/allteam`);
                const data = await reponse.json();
                console.log(data);
                setTeams(data);
            };

            fetchAllTeams(); 
        }, []
    );
    return (
        <div className="HomePage">
            <div className="header-section">
                <h1 className = "app-name">IPL Dashboard</h1>
            </div>
            <div className="team-grid">
                {teams.map(team => (
                    <div className="cells"><TeamTile teamName={team.teamName}></TeamTile></div>
                ))}
            </div>
        </div>
    );
}
