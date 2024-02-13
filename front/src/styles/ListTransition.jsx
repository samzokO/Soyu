export const transitionList = {
  hidden: {
    opacity: 0,
  },
  visible: {
    opacity: 1,
    transition: {
      when: 'beforeChildren',
      staggerChildren: 0.1,
    },
  },
};

export const transitionListitem = {
  hidden: { opacity: 0, y: 50 },
  visible: { opacity: 1, y: 0 },
};
