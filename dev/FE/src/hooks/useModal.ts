import { useState, useCallback } from 'react';

export default function useModal() {
  const [isOpen, setisOpen] = useState(false);

  const openToggle = useCallback(() => {
    setisOpen(true);
  }, []);

  const closeToggle = useCallback(() => {
    setisOpen(false);
  }, []);

  return {
    isOpen,
    openToggle,
    closeToggle,
  };
}
