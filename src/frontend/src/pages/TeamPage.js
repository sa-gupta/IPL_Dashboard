import { React, useEffect, useState } from 'react';
import {useParams, Link} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';
import {PieChart} from 'react-minimal-pie-chart';

import './TeamPage.scss';

export const TeamPage = () => {

    const [team, setTeam] = useState({matches: []});
    const { teamName } = useParams();

    useEffect(
        () => {
            const fetchMatches = async () => {
                const reponse = await fetch(`http://localhost:8087/team/${teamName}`);
                const data = await reponse.json();
                console.log(data);
                setTeam(data);
            };

            fetchMatches();
        }, [teamName]
    );


    if (!team || !team.teamName) {
        return <h1>Team Not Found !!</h1>
    }
    return (
        <div className="TeamPage">
            <div className="team-name-section">
                <h1 className = "team-name">{team.teamName}</h1>
            </div>
            <div className="win-loss-section">
                Wins / Losses
                <PieChart
                    data={[
                        {title: 'Lost', value: team.totalMatches - team.totalWins, color: '#ea3546'},
                        {title: 'Win', value: team.totalWins, color: '#498467'}
                    ]}
                />
            </div>
            <div className="match-detail-card">
                <h3>Latest Matches</h3>
                <MatchDetailCard teamName = {team.teamName} match={team.matches[0]} />
            </div>
            {team.matches.slice(1).map(match => <MatchSmallCard teamName = {team.teamName} match={match} />)}
            <div className="more-link">
            <Link to={`/teams/${teamName}/matches/${process.env.REACT_APP_DATA_END_YEAR}`}>
                More &gt;
            </Link>
            </div>
        </div>
    );
}
