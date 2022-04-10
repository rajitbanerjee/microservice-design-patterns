\ProvidesClass{./assets/tex/report-template}
\LoadClass[11pt, titlepage]{report}
\usepackage[paperwidth=21cm,paperheight=29.7cm,includehead,headheight=1.5cm,pdftex,hmargin={3cm,2.5cm},vmargin={0cm,2cm},]{geometry} 
\setlength{\parindent}{0cm} 
\renewcommand{\baselinestretch}{1.2}
\parskip=1em
\usepackage{float}

\renewcommand{\familydefault}{\sfdefault}
\renewcommand{\contentsname}{Table of Contents}

\usepackage[T1]{fontenc}
\usepackage{fancyhdr}
\usepackage{lastpage}
\usepackage{fancyhdr}
\pagestyle{fancy}
\fancypagestyle{plain}{}
\fancyhf{}
\fancyfoot[C]{Page {\thepage} of \pageref{LastPage}}

\renewcommand{\normalsize}{\fontsize{11pt}{11pt}\selectfont}
\renewcommand{\title}[1]{\textsf{\Huge{#1}}}
\renewcommand{\author}[1]{\textsf{\Huge{#1}}}
\newcommand{\subtitle}[1]{\textsf{\textbf{\Large{#1}}}}
\newcommand{\abstractheading}[1]{\textsf{\textbf{\Large{#1}}}}
\newcommand{\code}[1]{\texttt{\footnotesize{#1}}}
\newcommand*{\captionsource}[1]{Source: #1}

\renewcommand{\section}{\@startsection
{section}%                        % the name
{1}%                              % the level
{0mm}%                            % the indent
{10.6mm}%                         % the beforeskip
{4.2mm}%                          % the afterskip
{\Large\bfseries\sffamily}}       % the style

\renewcommand{\subsection}{\@startsection
{subsection}%                     % the name
{2}%                              % the level
{0mm}%                            % the indent
{6.4mm}%                          % the beforeskip
{1.1mm}%                          % the afterskip
{\large\bfseries\sffamily}}       % the style

\renewcommand{\subsubsection}{\@startsection
{subsubsection}%                  % the name
{3}%                              % the level
{0mm}%                            % the indent
{4.2mm}%                          % the beforeskip
{1.1mm}%                          % the afterskip
{\normalsize\bfseries\sffamily}}  % the style


\renewcommand\chapter{
\if@openright\cleardoublepage\else\clearpage\fi
                    \thispagestyle{plain}%
                    \global\@topnum\z@
                    \@afterindentfalse
                    \secdef\@chapter\@schapter}

\def\@makechapterhead#1{%
  { \parindent \z@ \raggedright \normalfont
    \ifnum \c@secnumdepth >\m@ne
        \huge\textsf{\@chapapp\space \thechapter:}
    \fi
    \interlinepenalty\@M
    \huge \bfseries \textsf{#1}\par\nobreak
    \rule{5cm}{0.5pt}
    \vskip 20\p@
  } }
  
\def\@makeschapterhead#1{%
  { \parindent \z@ \raggedright
    \normalfont
    \interlinepenalty\@M
    \huge \bfseries  \textsf{#1}\par\nobreak
    \rule{5cm}{0.5pt}
    \vskip 20\p@

  }}
  
 \renewenvironment{abstract}{%
      \chapter*{\abstractname}%
 }
     
     
\makeatletter
\renewcommand{\l@chapter}{\bfseries\@dottedtocline{1}{0em}{2.3em}}
\renewcommand{\l@section}{\normalfont\@dottedtocline{2}{2em}{2.3em}}
\renewcommand{\l@subsection}{\normalfont\@dottedtocline{3}{2em}{2.3em}}
\renewcommand{\l@subsubsection}{\normalfont\@dottedtocline{4}{2em}{2.3em}}
\makeatother

\def\maketitle{\begin{titlepage}
\thispagestyle{empty}
\let\footnotesize\small \let\footnoterule\relax \setcounter{page}{0}
\null
\vfil
\begin{center}
\title{Final Year Project\\[0.5cm]\rule{4cm}{1pt}\\[0.7cm]\textbf{\projecttitle}}\\[1cm]
\author{\Large \studentname}\\[0.5cm]\rule{4cm}{1pt}\\[0.5cm]
\textsf{\Large Student ID: \studentid}\\[0.5cm]\rule{4cm}{1pt}\\[0.5cm]
\textsf{\Large  A thesis submitted in part fulfilment of the degree of\\[0.5cm]
\textbf{BSc. (Hons.) in Computer Science}}\\[0.5cm]
\textsf{\Large  \textbf{Supervisor:}  \supervisorname}\\[1.5cm]
\includegraphics[height=5.5cm]{./assets/images/ucd-logo.pdf}\\[1cm]
\textsf{\Large UCD School of Computer Science\\
University College Dublin\\\vfill
\large \today}
\end{center}
\vfil
\null
\end{titlepage}}
