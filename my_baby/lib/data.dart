import 'models/resource.dart';

List<Section> data = [
  Section('COVID-19', [
    Subsection('What is COVID-19', [
      TextContent(
          'Coronavirus disease 2019 (COVID-19) is defined as illness caused by a novel coronavirus now called severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2; formerly called 2019-nCoV), which was first identified amid an outbreak of respiratory illness cases in Wuhan City, Hubei Province, China.It was initially reported to the WHO on December 31, 2019. On January 30, 2020, the WHO declared the COVID-19 outbreak a global health emergency.On March 11, 2020, the WHO declared COVID-19 a global pandemic, its first such designation since declaring H1N1 influenza a pandemic in 2009.'),
      ImageContent('assets/images/covid19.png'),
    ]),
    Subsection('COVID-19 signs', [
      TextContent(
          'COVID-19 affects different people in different ways. Most infected people will develop mild to moderate illness and recover without hospitalization.'),
      BulletListContent('Most common symptoms:', ['fever', 'dry cough', 'tiredness']),
      BulletListContent('Less common symptoms:', [
        'aches and pains',
        'sore throat',
        'diarrhea',
        'conjunctivitis',
        'headache',
        'loss of taste or smell',
        'a rash on skin, or discoloration of fingers or toes'
      ]),
      BulletListContent('Serious symptoms:', [
        'difficulty breathing or shortness of breath',
        'chest pain or pressure',
        'loss of speech or movement'
      ]),
      TextContent(
          'Seek immediate medical attention if you have serious symptoms. Always call before visiting your doctor or health facility.\n'
              'People with mild symptoms who are otherwise healthy should manage their symptoms at home.\n'
              'On average it takes 5–6 days from when someone is infected with the virus for symptoms to show, however it can take up to 14 days.'),
    ]),
    Subsection('How to deal with COVID-19', null),
    Subsection('Masks', null),
    Subsection('Medical Instructions', null),
    Subsection('Quarantine Instructions', null),
    Subsection('Hot Line', null)
  ]),
  Section('Labor and Delivery', null),
  Section('Breast Feeding', null),
  Section('Baby', null),
  Section('Exercise', null),
  Section('Doctor Consultant', null),
  Section('Community Experience', null)
];
