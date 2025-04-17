import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import { updateMilestone } from '../services/api';

                                              

const MilestoneEdit = ({ milestone, onUpdate }) => {
  const [date, setDate] = useState(new Date(milestone.date));
  const [title, setTitle] = useState(milestone.title);

                                           

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateMilestone(milestone._id, {
        date,
        title
      });
                                                

      onUpdate();
    } catch (error) {
      console.error('Failed to update milestone:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <DatePicker
        selected={date}
        onChange={setDate}
        dateFormat="yyyy-MM-dd"
      />
      <input
        type="text"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <button type="submit">Save Changes</button>
    </form>
  );
};

export default MilestoneEdit;